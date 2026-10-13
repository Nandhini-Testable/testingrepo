from __future__ import annotations

from .visit_log import LogVisitor
from .transform_log import LogTransformer
import sys
from .decisao import Decisao
import argparse
import types
import ast
import shlex
import pickle
import unittest
import time
import os
import importlib.util
from pathlib import Path
from pymcdc.mcdc_html_generator import generate_mcdc_html
import pymcdc.verifica_mcdc as vf

# no início da execução de uma decisão, coloca a linha no topo da pilha
# ao finakl da execução, e linha é retirada da pilha 
pilha_de_execucao = []

# nesse dicionário são incluídos os rastros de execução, por linha
# por exemplo:
# {(10,5): [ [(0,1), (1,0), (2,1)], [(1,1), (2,0)] ]
dic_of_traces = {}


def init_bool_register(linha, coluna, ret):
	# print('Iniciando {} - {}'.format( (linha,coluna), ret))
	l = []
	if (linha,coluna) in dic_of_traces:
		dic_of_traces[(linha,coluna)].append(l)
	else:
		dic_of_traces[(linha,coluna)] = [ l ]
	pilha_de_execucao.append(((linha,coluna), l))
	# print('Dic of traces', dic_of_traces)
	return ret 


def bool_register(result, linha, coluna, condicao):
	# print('Decisao: {} Condição: {} Resultado: {}'.format((linha,coluna), condicao, result))
	lc = (linha,coluna)
	lc_da_pilha, rastro = pilha_de_execucao[-1] 
	try: 
		if lc != lc_da_pilha:
			raise ValueError('Numero de linha inesperado {}'.format(lc)) 
	except Exception as ex:
		print(f'Invalid line number: {lc}')
		raise ex
	r = 1 if result else 0
	rastro.append((condicao, r))
	
	return result

def end_bool_register(ret):
	lc = pilha_de_execucao.pop()
	# print(f'Finalizando {lc} valor: {ret}')
	return ret


def _to_module_name(key: str) -> str:
	# "dir/arquivo.py" -> "dir.arquivo"
	# "dir\\arquivo.py" (Windows) -> "dir.arquivo"
	# "arquivo.py" -> "arquivo"
	# "dir.arquivo" -> "dir.arquivo"
	if key.endswith(".py"):
		key = os.path.splitext(key)[0]
	key = key.replace(os.sep, ".")
	return key

def _ensure_module_hierarchy(module_name: str):
	parts = module_name.split(".")
	parent = None
	fullname = ""
	for part in parts[:-1]:
		fullname = f"{fullname}.{part}" if fullname else part
		if fullname in sys.modules:
			parent = sys.modules[fullname]
			continue
		# verifica se é um pacote real no disco
		if importlib.util.find_spec(fullname) is not None:
			# importa o pacote de verdade
			__import__(fullname)
			parent = sys.modules[fullname]
			continue
		# cria pacote "fake" só se não existe nem no sys.modules nem no disco
		pkg = types.ModuleType(fullname)
		pkg.__path__ = []  # pacote vazio
		sys.modules[fullname] = pkg
		if parent is not None:
			setattr(parent, part, pkg)
		parent = pkg
	return parent, parts
	

def _install_module(module_name: str, code_obj, inject: dict | None = None):
	"""
	Cria a hierarquia, executa code_obj no módulo final e registra em sys.modules.
	inject: dict com símbolos extras a injetar no módulo antes do exec.
	"""
	parent, parts = _ensure_module_hierarchy(module_name)
	mod = types.ModuleType(module_name)
	if inject:
		mod.__dict__.update(inject)
	exec(code_obj, mod.__dict__)
	sys.modules[module_name] = mod
	if parent is not None:
		setattr(parent, parts[-1], mod)
	return mod

def run_ast_tests(modules: dict, test_modules: dict, *, runner=None, verbosity=1):
	"""
	Compila e executa módulos e testes em memória usando ASTs.

	- modules: dict nome|path -> AST (ex: {'x.py': ast_x, 'pkg/aux.py': ast_aux})
	- test_modules: dict nome|path -> AST (ex: {'test_x.py': ast_tx})

	Retorna: unittest result object
	"""
	loaded_test_mods = []

	# === Etapa 1: carrega módulos normais (CUTs) ===
	for key, tree in modules.items():
		mod_name = _to_module_name(key)
		ast.fix_missing_locations(tree)
		
		#print(ast.unparse(tree))
		code_obj = compile(tree, f"<{mod_name}>", "exec")
		# injeta dependências (ajuste se as funções tiverem outro nome/escopo)
		inject = {}
		if "bool_register" in globals():
			inject["bool_register"] = globals()["bool_register"]
		if "init_bool_register" in globals():
			inject["init_bool_register"] = globals()["init_bool_register"]
		if "end_bool_register" in globals():
			inject["end_bool_register"] = globals()["end_bool_register"]
			
		_install_module(mod_name, code_obj, inject=inject)

	# === Etapa 2: carrega módulos de teste ===
	for key, tree in test_modules.items():
		mod_name = _to_module_name(key)
		ast.fix_missing_locations(tree)
		code_obj = compile(tree, f"<{mod_name}>", "exec")
		test_mod = _install_module(mod_name, code_obj)
		loaded_test_mods.append(test_mod)

	# === Etapa 3: executa os testes ===
	suite = unittest.TestSuite()
	loader = unittest.defaultTestLoader
	for test_mod in loaded_test_mods:
		suite.addTests(loader.loadTestsFromModule(test_mod))

	if runner is None:
		runner = unittest.TextTestRunner(verbosity=verbosity)
	result = runner.run(suite)
	return result



def parse_args():

	# print("args.unittest:", args.unittest)
	# print("args.run:", args.run)
	# print("args.path:", args.path)
	# print("args.append:", args.append)
	# print("args.args:", args.args)
	# print("args.arquivo:", args.arquivo)

	args = Argumento()
	i = 1
	if sys.argv[i] == '--run':
		args.run = True
		i += 1


	while i < len(sys.argv) - 1:
		opt = sys.argv[i]
		if opt == '--path':
			i += 1
			args.path.append(sys.argv[i])
		elif opt == '--unittest':
			if args.run:
				usage()
			i += 1
			args.unittest.append(sys.argv[i])
		elif opt == '--append':
			args.append = True
		elif opt == '--assert':
			args.asserti = True
		elif opt == '--args':
			i += 1
			s = sys.argv[i]
			args.args = shlex.split(s)
		else:
			usage()
		i += 1
	
	if i != len(sys.argv) - 1:
		usage()

	args.arquivo = sys.argv[i]
	return args
def usage():
	print("pymcdc — V 0.2.6 — Modified Condition/Decision Coverage (MC/DC) Analyzer")
	print("--------------------------------------------------------------")
	print("Analyze and verify MC/DC criteria for Python programs.")
	print()
	print("Usage:")
	print("  python -m pymcdc [options] <source_file>")
	print()
	print("Main options:")
	print("  --run            Execute the program and show which MC/DC combinations were covered")
	print("  --append         Accumulate coverage data across multiple runs")
	print("  --unittest FILE  Run the source file using tests defined in FILE (can be used multiple times)")
	print("  --cover [+|-]L C R  Manually set requirement R of decision (L, C) as covered (+) or not (-)")
	print("  --assert         Do not skip assert commands while analyzing MC/DC requirements")
	print("  --html           Generate a report in a html file. Easier to manipulate")
	print("  --path   <PATH>  Include the <PATH> in the include path to execute unittest files" )
	print("  --total          Show only the total and covered numbers for MC/DC requirements" )
	print()	
	print("Examples:")
	print("  python -m pymcdc foo.py")
	print("     → Analyze foo.py and display required condition combinations for each decision")
	print()
	print("  python -m pymcdc --run foo.py")
	print("     → Execute foo.py and display which MC/DC combinations were covered during execution")
	print()
	print("  python -m pymcdc --run --append foo.py")
	print("     → Cumulatively execute foo.py, aggregating MC/DC results from multiple runs")
	print()
	print("  python -m pymcdc --unittest test_foo.py foo.py")
	print("     → Run foo.py using unit tests from test_foo.py and show MC/DC coverage results")
	print()
	print("  python -m pymcdc --cover +5 5 1 -18 5 3 foo.py")
	print("     → Mark requirement 1 of decision (5,5) as covered and requirement 3 of (18,5) as not covered")
	print()
	print("  python -m pymcdc --html foo.py")
	print("     → Creates an html report in fle html/foo.py/index.html")
	print()
	print("Notes:")
	print("  • The number of MC/DC requirements for a decision with n conditions is not always n+1.")
	print("    It may be slightly larger due to algorithmic limitations.")
	print("  • For decisions with more than 15 conditions, analysis may take several minutes.")
	print()
	print("Installation:")
	print("  pip install pymcdc")
	print()
	print("Author: Marcio Delamaro")
	print("License: MIT")
	print()
	print("For more information, visit the project page.")
	sys.exit(1)

def __ignore():
	i = 2
	n = len(sys.argv)
	arquivo = sys.argv[-1]
	vetig = []
	try: 
		while i < n - 1:
			s = sys.argv[i]
			if s.startswith('+'):
				l = int(s[1:])
				i += 1
				c = int(sys.argv[i])
				i += 1
				g = int(sys.argv[i])
				i += 1
				vetig.append(('+', l, c, g))
			elif s.startswith('-'):
				l = int(s[1:])
				i += 1
				c = int(sys.argv[i])
				i += 1
				g = int(sys.argv[i])
				i += 1
				vetig.append(('-', l, c, g))
			else:
				raise ValueError(f'Invalid argumet {s}')
	except Exception as ex:
		print(red('Invalid argumet'))
		print(red(ex))
		return

	
	try:
		with open(arquivo+'.mdc','rb') as f:
			dic_decisoes = pickle.load(f)
	except Exception as ex:
		print(red('Can´t read execution log file'))
		print(red(ex))
		return


	for op,l,c,oq in vetig:
		exect = op == '+'
		achou = False
		for _, dec in dic_decisoes.items():
			if dec.linha == (l,c):
				achou = True
				try:
					dec.executado[oq-1] = exect
					#print(dec)
				except:
					print(red(f'Not found requirement {(l,c,oq)}'))
				break
		if not achou:
			print(red(f'Not found decision {(l,c)}'))

	print_all_conditions(dic_decisoes)

	with open(arquivo+'.mdc','wb') as f:
		pickle.dump(dic_decisoes, f)

def html_all_conditions(arquivo, conditions_list):
	path = Path(arquivo)
	totcov = totreq = 0
	l = []
	dic = { 'path': path.parent / 'html' / path.name, 'name': path.name, 'conditions': l}
	for _, dec in conditions_list.items():
		linhas = dec.get_cond_table()
		linhas = linhas.splitlines()
		ok, pares = vf.verifica_mcdc(linhas)
		p = []
		if ok:
			for _,c in pares.items():
				c1,c2 = c
				i1 = linhas.index(c1) + 1
				i2 = linhas.index(c2) + 1
				p.append( (i1,i2) )
		dic_dec = {'Line Number': dec.linha, #Tupla (linha,coluna),
					'Decision': dec.texto, #str
					'Content' : dec.to_table(),
					'pairs' : p
				}
		l.append(dic_dec)
		cov,req = dec.get_covered_requirements()
		totcov += cov
		totreq += req
	
	dic['totals'] =  (totcov,totreq)
	return dic



def print_all_conditions(conditions_list):
	totcov = totreq = 0
	for _,dec in conditions_list.items():
		print('Line number: {}'.format(dec.linha))
		print('Decision:', dec.texto)
		print('Combinations to be covered: ')
		print(dec)
		cov,req = dec.get_covered_requirements()
		totcov += cov
		totreq += req
	percent = totcov * 100 // totreq if totreq != 0 else 100
	print(green('''\nCovered {} out of {} requirements in {} decisions ({}%)
	      '''.format(totcov, totreq, len(conditions_list),percent)))

def __total():
	arquivo = sys.argv[-1]
	debug = False
	try:
		if sys.argv[-2] == '--debug':
			debug = True
			del sys.argv[-2]
	except:
		print(red('Invalid argumet'))
		sys.exit(1)

	if len(sys.argv) != 3:
		print(red('Invalid argumet'))
		sys.exit(1)
	try:
		with open(arquivo+'.mdc','rb') as f:
			dic_decisoes = pickle.load(f)
	except Exception as ex:
		print(red('Can´t read execution log file'))
		print(red(ex))
		sys.exit(1)

	totcov = totreq = 0
	for _,dec in dic_decisoes.items():
		#print(dec)
		cov,req = dec.get_covered_requirements()
		totcov += cov
		totreq += req
		if debug:
			linhas = dec.get_cond_table()
			linhas = linhas.splitlines()
			ok, pares = vf.verifica_mcdc(linhas)
			#print(pares)
			if ok:
				for l,c in pares.items():
					c1,c2 = c
					i1 = linhas.index(c1) + 1
					i2 = linhas.index(c2) + 1
					print(f'Condition {l}) \n\t{i1}. {c1[0]} {c1[1:]}\n\t{i2}. {c2[0]} {c2[1:]}')
			print(f'{dec.linha} is MCDC: {ok}\n')
		#print(dec.get_cond_table())
	
	print(totcov, totreq)

def __html():
	arquivo = sys.argv[-1]
	if len(sys.argv) != 3:
		print(red('Invalid argumet'))
		sys.exit(1)
	try:
		with open(arquivo+'.mdc','rb') as f:
			dic_decisoes = pickle.load(f)
	except Exception as ex:
		print(red('Can´t read execution log file'))
		print(red(ex))
		sys.exit(1)

	dic = html_all_conditions(arquivo, dic_decisoes)
	generate_mcdc_html(dic)
	relativ_p = Path(f'{dic["path"]}/index.html')
	print(green(f'Html file generated at: {relativ_p.resolve().as_uri()}'))


def main():
	
	if len(sys.argv) < 2:
		sys.exit()
	if sys.argv[1] == '--cover':
		__ignore()
		sys.exit()	
	if sys.argv[1] == '--total':
		__total()
		sys.exit()
	if sys.argv[1] == '--html':
		__html()
		sys.exit()

	args = parse_args()
	arquivo = args.arquivo

	# print("args.unittest:", args.unittest)
	# print("args.run:", args.run)
	# print("args.path:", args.path)
	# print("args.append:", args.append)
	# print("args.args:", args.args)
	# print("args.arquivo:", args.arquivo)
 	

	if not (args.run or args.unittest):
		inicio = time.time()
		try:
			mv = LogVisitor(file=arquivo, doassert=args.asserti)
		except Exception as ex:
			print(red('Error using {}. {}'.format(arquivo, str(ex))))
			sys.exit(1)
		mv.go_visit()


		temtypeguard  = find_typeguard_import(mv.tree)

		if temtypeguard is not None:
			line, import_statement = temtypeguard

			print(red(
				f"{arquivo}:{line}: Typeguard usage detected: "
				f"'{import_statement}'.\n"
				"The instrumentation performed by Typeguard is incompatible "
				"with cfgcoverage instrumentation.\n"
				"Remove Typeguard from the program before running "
				"MC/DC analysis."
			))
			sys.exit(1)

		dic_decisoes = {}
		for linha,cond in mv.decisoes.items():
			if  any(item in cond for item in ['+','*']):
				texto = mv.textos[linha]
				dic_decisoes[linha] = (Decisao(linha, cond, texto))
		
		#print(dic_decisoes)

		print_all_conditions(dic_decisoes)
			
		with open(args.arquivo+'.mdc','wb') as f:
			pickle.dump(dic_decisoes, f)
		fim = time.time()
		print('Run time: {:.5f} '.format( fim-inicio))
							
	elif args.run:
		try:
			mv = LogTransformer(file=arquivo, doassert=args.asserti)
		except Exception as ex:
			print(red('Error using {}. {}'.format(arquivo, str(ex))))
			sys.exit(1)
		mv.go_visit()
		
		# insere os caminhos para include
		for caminho in args.path:
			if caminho not in sys.path:
				sys.path.insert(0, caminho)
				
		# Cria um módulo virtual e injeta o canal no namespace
		mod = types.ModuleType("__main__")
		mod.__dict__['bool_register'] = bool_register  # injeta a função
		mod.__dict__['init_bool_register'] = init_bool_register  # injeta a função
		mod.__dict__['end_bool_register'] = end_bool_register  # injeta a função

		mv.tree = ast.fix_missing_locations(mv.tree)

		#print(ast.unparse(mv.tree))

		oldargv = sys.argv
		sys.argv = [args.arquivo]
		sys.argv += args.args

		# print(mv.decisoes)
		# print(ast.dump(mv.tree))
		# print()
		# print(ast.unparse(mv.tree))
		# Executa a AST no namespace do módulo
		exec(compile(mv.tree, "<main>", "exec"), mod.__dict__)
		
		sys.argv = oldargv
		
		leu = False
		if args.append:
			try:
				with open(args.arquivo+'.mdc','rb') as f:
					dic_decisoes = pickle.load(f)
				leu = True
			except:
				print(red('Can´t read execution log file'))

		if not leu:
			dic_decisoes = {}
			for linha,cond in mv.decisoes.items():
				if  any(item in cond for item in ['+','*']):
					texto = mv.textos[linha]
					dic_decisoes[linha] = (Decisao(linha, cond, texto))
			
			
		# print(pilha_de_execucao)
		# print(dic_of_traces)

		for linha, rastros  in dic_of_traces.items():
			if linha in dic_decisoes:
				dec = dic_decisoes[linha]
				dec.executa(rastros)

		print_all_conditions(dic_decisoes)

		with open(args.arquivo+'.mdc','wb') as f:
			pickle.dump(dic_decisoes, f)
		
	elif args.unittest:
		try:
			mv = LogTransformer(file=arquivo, doassert=args.asserti)
		except Exception as ex:
			print(red('Error using {}. {}'.format(arquivo, str(ex))))
			sys.exit(1)
		mv.go_visit()

		
		# insere os caminhos para include
		for caminho in args.path:
			if caminho not in sys.path:
				sys.path.insert(0, caminho)
		
		mod_dic = {arquivo:mv.tree}
		test_dic = {}
		for name in args.unittest:
			try: 
				f = open(name)
				source = f.read()
				f.close()
				code = ast.parse(source)
			except Exception as ex:
				print(red('Error using {}. {}'.format(arquivo, str(ex))))
				sys.exit(1)
			test_dic[name] = code
			
		result = run_ast_tests(mod_dic,test_dic)
		
		leu = False
		if args.append:
			try:
				with open(args.arquivo+'.mdc','rb') as f:
					dic_decisoes = pickle.load(f)
				leu = True
			except:
				print(red('Can´t read execution log file'))

		if not leu:
			dic_decisoes = {}
			for linha,cond in mv.decisoes.items():
				if  any(item in cond for item in ['+','*']):
					texto = mv.textos[linha]
					dic_decisoes[linha] = (Decisao(linha, cond, texto))
			
			
		for linha, rastros  in dic_of_traces.items():
			if linha in dic_decisoes:
				dec = dic_decisoes[linha]
				dec.executa(rastros)

		print_all_conditions(dic_decisoes)
			
		with open(args.arquivo+'.mdc','wb') as f:
			pickle.dump(dic_decisoes, f)		


def find_typeguard_import(tree: ast.AST) -> tuple[int, str] | None:
	"""
	Procura imports do pacote Typeguard.

	Retorna uma tupla contendo o número da linha e a representação
	do import encontrado. Retorna None se não houver import.
	"""
	for node in ast.walk(tree):
		if isinstance(node, ast.Import):
			for alias in node.names:
				if (
					alias.name == "typeguard"
					or alias.name.startswith("typeguard.")
				):
					return node.lineno, ast.unparse(node)

		elif isinstance(node, ast.ImportFrom):
			if (
				node.module == "typeguard"
				or (
					node.module is not None
					and node.module.startswith("typeguard.")
				)
			):
				return node.lineno, ast.unparse(node)

	return None



class Argumento():

	def __init__(self):
		self.args = []
		self.run = False
		self.unittest = []
		self.path = []
		self.arquivo = None
		self.append = False
		self.asserti = False
		
RED = '\033[31m'
GREEN = '\033[032m'
RESET = '\033[0m'

def red(s):
	if os.getenv('PYMCDCCOLOR') != None :
		return RED + str(s) + RESET
	return s 

def green(s):
	if os.getenv('PYMCDCCOLOR') != None :
		return GREEN + str(s) + RESET
	return s 

if __name__ == '__main__':
	main()
		

	
