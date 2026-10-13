from .visit_log import LogVisitor
from collections import Counter
from typing import List, Set, Tuple
from functools import lru_cache
import math
from itertools import combinations



class Decisao:
	operadores = ['*','+','-']

	def __init__(self, linha, decisao, texto):
		self.condicoes = []
		self.linha = linha
		self.texto = texto
		self.decisao = decisao[:]
		s = '{:3} | {:^8}'

		for no in decisao:
			if no in self.operadores:
				continue
			self.condicoes.append(no)
			lein = len(no) if len(no) >= 5 else 5
			s += '{:^'+str(lein+4)+'}'
		s += '{:^8}'
		self.computa_resultados()
		self.computa_pares()
		self.computa_requisitos()
		self.executado = len(self.requisitos) * [False]
		self.format = s
		
	def get_covered_requirements(self):
		return self.executado.count(True), len(self.executado)

		
	def __str__(self):
		l = [' ', 'Result.']
		for cd in self.condicoes:
			l.append(cd)
		l.append('Cover.')
		s = self.format.format(*l)
		r = len(s)
		s += '\n'
		s += r * '-'
		s += '\n'

		j = 0
		for p1 in self.requisitos:
			l = [j+1]
			l.append(str(result := self.resultados[p1]))
			pilha = self.decisao2pilha(p1)
			_,usado = self.computa_decisao(pilha)
			listap1 = Decisao.int_to_list(p1,len(self.condicoes))
			for idx,r in enumerate(listap1):

				if idx in usado:
					l.append(str(r == 1))
				else:
					l.append('----')

			l.append(str(self.executado[j]))
			j += 1
			s += self.format.format(*l)+'\n'
				
		return s

	def get_cond_table(self):
		linhas = ''
		for p1 in self.requisitos:
			l = ''
			l += 'V' if self.resultados[p1] else 'F'
			pilha = self.decisao2pilha(p1)
			_,usado = self.computa_decisao(pilha)
			listap1 = Decisao.int_to_list(p1,len(self.condicoes))
			for idx,r in enumerate(listap1):

				if idx in usado:
					l += 'V' if r == 1 else 'F'
				else:
					l += 'X'

			linhas += l + '\n'
		return linhas

	def to_table(self):
		listao = []
		l = ['Result.']
		for cd in self.condicoes:
			l.append(cd)
		l.append('Cover.')
		listao.append(l)

		j = 0
		for p1 in self.requisitos:
			l = []
			l.append(str(result := self.resultados[p1]))
			pilha = self.decisao2pilha(p1)
			_,usado = self.computa_decisao(pilha)
			listap1 = Decisao.int_to_list(p1,len(self.condicoes))
			for idx,r in enumerate(listap1):

				if idx in usado:
					l.append(str(r == 1))
				else:
					l.append('----')

			l.append(str(self.executado[j]))
			j += 1
			listao.append(l)
		return listao


	@staticmethod
	def int_to_list(k, n):
		l = []
		for i in range(n):
			r = k & 1
			l.append(r)
			k >>= 1
		return l
	
	@staticmethod
	def list_to_int(listal):
		intk = 0
		b = 1
		for r in listal:
			if r == 1:
				intk += b
			b *= 2
		return intk
	
	def computa_requisitos(self):
		req = set()
		for p1,p2 in self.pares:
			req.add(p1)
			req.add(p2)
		self.requisitos = sorted(list(req))
	
	def computa_resultados(self):
		self.resultados = []
		max = 2 ** len(self.condicoes)
		for k in range(max):
			if ( len(self.condicoes) > 18) :
				print('Computing: {} of {}.'.format(k,max), end='\r')
			pilha = self.decisao2pilha(k)
			self.resultados.append(self.computa_decisao(pilha)[0])

	def decisao2pilha(self, k):
		pilha = self.decisao[:]
		blip = k
		for i in range(len(pilha)):
			if pilha[i] in self.operadores:
				continue
			if blip & 1  == 1:
				pilha[i] = True
			else:
				pilha[i] = False
			blip >>= 1
		return pilha


	def computa_decisao(self, pilha):
		i = 0
		for j,no in enumerate(pilha) :
			if no not in self.operadores:
				pilha[j] = (no,i)
				i += 1

		usei = set()
		i = 0
		while len(pilha) > 1:
			if not pilha[i] in self.operadores:
				i += 1
				continue
			if pilha[i] == '*':
				leftv,leftidx =  pilha[i-2]
				usei.add(leftidx)					
				rightv,rightidx = pilha[i-1]
				if leftv:
					usei.add(rightidx)
				r = leftv and rightv 
			elif pilha[i] == '+':
				leftv,leftidx =  pilha[i-2]
				usei.add(leftidx)					
				rightv,rightidx = pilha[i-1]
				if not leftv:
					usei.add(rightidx)
				r = leftv or rightv 
			else:
				leftv,leftidx =  pilha[i-1]
				usei.add(leftidx)					
				r = not leftv				
			if pilha[i] != '-':
				pilha[i-2] = (r,-1)
				del pilha[i-1]
				del pilha[i-1]
				
			else:
				pilha[i-1] = (r,-1)
				del pilha[i]
			i = 0
		return pilha[0][0], usei
		
		
	def computa_pares(self):
		n = len(self.condicoes) # numero de decisoes é o numero de dígitos
		self.pares = []
		for i in range(n):
			usados = set()
			self.pares.append(set())
			for j in range(2**n):
				if j in usados :
					continue
				usados.add(j)
				par = j + 2**i
				if par >= 2**n:
					break
				usados.add(par)
				if self.resultados[j] != self.resultados[par]:
					self.pares[i].add((j,par))
		self.pares = self.minimiza_pares(self.pares)
				

	def minimiza_pares(self, lista_conjuntos):
		elementos = {
			x
			for conjunto in lista_conjuntos
			for par in conjunto
			for x in par
		}

		numero_condicoes = len(self.condicoes)
		n = len(elementos)
		k = numero_condicoes + 1

		#print(n, k, math.comb(n,k))
		if math.comb(n, k) <= 1_000_000:
			return self.minimiza_heuristica(lista_conjuntos, k)
		else:
			return self.minimiza_heuristica(lista_conjuntos)		

	def minimiza_heuristica(self,lista_conjuntos,k=0):
		resultado = set()
		usados = Counter()

		for conjunto in lista_conjuntos:
			melhor_par = None
			melhor_pontuacao = -1

			for par in conjunto:
				a, b = par
				pontuacao = usados[a] + usados[b]
				if pontuacao > melhor_pontuacao:
					melhor_pontuacao = pontuacao
					melhor_par = par

			resultado.add(melhor_par)
			usados[melhor_par[0]] += 1
			usados[melhor_par[1]] += 1

		return resultado

	def minimiza_exato(self, lista_conjuntos, minimo_elementos):
		elementos = set()

		for conjunto in lista_conjuntos:
			for a, b in conjunto:
				elementos.add(a)
				elementos.add(b)

		for subconjunto in combinations(elementos, minimo_elementos):
			subconjunto = set(subconjunto)

			if all(
				any(a in subconjunto and b in subconjunto for a, b in conjunto)
				for conjunto in lista_conjuntos
			):
				resultado = set()

				for conjunto in lista_conjuntos:
					for par in conjunto:
						a, b = par
						if a in subconjunto and b in subconjunto:
							resultado.add(par)
							break

				return resultado

		return None

# execuções é um dicionário com o valor de cada uma das condições 
	def executa(self, execucoes):
		for uma_execucao in execucoes:
			for i in range(len(self.requisitos)):
				req = self.requisitos[i]
				lreq = Decisao.int_to_list(req, len(self.condicoes))
				executou = True
				for cond,res in uma_execucao:
					if lreq[cond] != res:
						executou = False
						break
				self.executado[i] |= executou
		return


