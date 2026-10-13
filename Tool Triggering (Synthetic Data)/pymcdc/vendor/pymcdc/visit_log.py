
import ast,sys,inspect


class LogVisitor(ast.NodeVisitor):
	
	def __init__(self, file=None, code=None, doassert=False):
		if file:
			f = open(file)
			self.code_snipet = f.read()
			f.close()
		elif code:
			self.code_snipet = code
		else:
			self.code_snippet = ''
			
		self.level = 0
		self.linhas = self.code_snipet.splitlines()
		self.tree = ast.parse(self.code_snipet)
		self.decisoes = {}
		self.textos = {}
		self.doassert = doassert
		

	def visit_BoolOp(self, node):
		if self.level == 0:
			self.linha = (node.lineno,node.col_offset)
			self.decisoes[self.linha] = []
			self.textos[self.linha] = self.to_src(node)
		k = 0
		for no in node.values:
			if not isinstance(no,(ast.BoolOp,ast.UnaryOp)):
				self.decisoes[self.linha].append(self.to_src(no))
				# self.level += 1
				# self.visit(no)
				# self.level -= 1
			else:
				self.level += 1
				self.visit(no)
				self.level -= 1
			if k != 0:
				self.decisoes[self.linha].append('*' if isinstance(node.op,ast.And) else '+')  
			k += 1

	def visit_UnaryOp(self, node):
		if not isinstance(node.op, ast.Not):
			return
		if self.level == 0:
			self.linha = (node.lineno,node.col_offset)
			self.decisoes[self.linha] = []
			self.textos[self.linha] = self.to_src(node)
		no = node.operand
		if not isinstance(no,(ast.BoolOp,ast.UnaryOp)):
			self.decisoes[self.linha].append(self.to_src(no))
			# self.level += 1
			# self.visit(no)
			# self.level -= 1
		else:
			self.level += 1
			self.visit(no)
			self.level -= 1
		self.decisoes[self.linha].append('-')  

	def visit_Assert(self, node) :
		if self.doassert:
			self.generic_visit(node)

	
	def to_src(self, node):
		l = node.lineno-1
		lf = node.end_lineno-1
		s = ''
		c = node.col_offset
		cf = node.end_col_offset
		while l <= lf:
			if l == lf:
				s += self.linhas[l][c:cf].strip()
			else:
				s += self.linhas[l][c:].strip()
			l += 1
			c = 0
		return s
			 

	def go_visit(self):
		self.visit(self.tree)


	
