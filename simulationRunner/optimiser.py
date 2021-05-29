from scipy.optimize import differential_evolution, basinhopping
from subprocess import check_output
import numpy as np

population = 100
numberOfDays = 100
maximumDailyUsableVolume = 9

def simulation(x):
    out = check_output(['java', '-jar', 'simulation.jar', str(x[0]), str(x[1]), str(x[2]), str(x[3]), str(x[4]), str(x[5]), str(x[6]), str(population), str(numberOfDays), str(maximumDailyUsableVolume)])
    data = list(map(lambda x: float(x), out.split()))

    last_ten_averaged = np.average(data[-10:]) 

    return 10 - last_ten_averaged

bounds = [(0, 1), (0, 1), (0, 1), (0, 1), (0, 1), (0, 1), (0, 1)]

####

def diff_evolution():
  return differential_evolution(simulation, bounds, maxiter=30)

#####

class MyBounds(object):
    def __init__(self, xmin=[0,0,0,0,0,0,0]):
        self.xmin = np.array(xmin)

    def __call__(self, **kwargs):
        x = kwargs["x_new"]
        tmin = bool(np.all(x >= self.xmin))
        return tmin

def basinhop():
    mybounds = MyBounds()
    x0 = [0.7039219, 0.53320619, 0.16432764, 0.02183199, 0.79795581, 0.04360191, 0.91295635]
    return basinhopping(simulation, x0, accept_test=mybounds)

result = basinhop()

print("Population:", population)
print("Number of days:", numberOfDays)
print("Max daily usable volume:", maximumDailyUsableVolume)
print(result.x, result.fun)