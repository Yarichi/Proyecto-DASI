from Queue import Queue
import cProfile
import re
class CalculoRutas:
	
	def __init__(self,(xIni,yIni),obstacles, w, h):
		self.obstaculos = obstacles #Inicialmente vamos a pensarlo como una lista de tuplas (x,y)
		self.xInicial = xIni
		self.yInicial = yIni
		self.cola = Queue()
		self.grid = {} # Doble diccionario indexando primero por la x (fila)
		self.width = int(w)
		self.height = int(h)
		
	def calculaRuta(self,dx,dy):
		self.cola = Queue()
		self.cola.put((self.xInicial,self.yInicial))
		self.grid["%i"%(self.xInicial)] = {}
		self.grid["%i"%(self.xInicial)]["%i"%(self.yInicial)] = 0
		while not self.cola.empty():
			coords = self.cola.get()
			if coords[0] == dx and coords[1] == dy:
				return self.generaRutaEncontrada((dx,dy))
			else:
				self.generarPuntos(coords)
		return None
	def generarPuntos(self,(x,y)):
		#Comprobaciones de los limites del mapa
		for i in [-1,1]:
			if x+i >= 0 and x+i < self.width and not self.inGrid(x+i, y) and not self.isAObstacle((x+i,y)):
				if "%i"%(x+i) not in self.grid:
					self.grid["%i"%(x+i)] = {}
				self.grid["%i"%(x+i)]["%i"%(y)] = self.grid["%i"%(x)]["%i"%(y)] + 1;
				self.cola.put((x+i,y))
		for i in [-1,1]:
			if y+i >= 0 and y+i < self.height and not self.inGrid(x, y+i) and not self.isAObstacle((x,y+i)):
				self.grid["%i"%(x)]["%i"%(y+i)] = self.grid["%i"%(x)]["%i"%(y)]+1;
				self.cola.put((x,y+i))
	
	def inGrid(self, x, y):
		return (("%i"%(x)) in self.grid) and (("%i"%(y)) in self.grid["%i"%(x)])
	
	
	def getMinimoAlrededor(self,auxCoor):
		minx = auxCoor[0]
		miny = auxCoor[1]
		x = auxCoor[0]
		y = auxCoor[1]
		for i in [-1,0,1]:
			for j in [-1,0,1]:
				if x+i >=0 and y+j>=0 and x+i<self.width and y+j<self.height and self.inGrid(x+i, y+j):
					if self.grid["%i"%(x+i)]["%i"%(y+j)] >-1 and self.grid["%i"%(x+i)]["%i"%(y+j)] < self.grid["%i"%(minx)]["%i"%miny]:
						minx = x+i
						miny = y+j
		return (minx,miny)
	
	def generaRutaEncontrada(self,(x,y)):
		ruta = []
		aux = (x,y)
		while(x != self.xInicial or y != self.yInicial):
			x = aux[0]
			y = aux[1]
			ruta.insert(0, (x,y))
			aux = self.getMinimoAlrededor(aux)
		return ruta
	def isAObstacle(self,coor):
		return coor in self.obstaculos
		



#if __name__ == "__main__":
