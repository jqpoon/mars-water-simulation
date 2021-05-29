from scipy.optimize import differential_evolution, basinhopping
from subprocess import check_output
import numpy as np

population = 100
numberOfDays = 10
maximumDailyUsableVolume = 5

# Run the simulation with provided values
def simulation(x):
    out = check_output(['java', '-jar', 'pandemic.jar', str(x[0]), str(x[1]), str(x[2]), str(x[3]), str(x[4]), str(x[5]), str(x[6]), str(population), str(numberOfDays), str(maximumDailyUsableVolume)])
    data = list(map(lambda x: float(x), out.split()))
    last_ten_averaged = np.average(data[-10:]) 

    return 10 - last_ten_averaged

####

bounds = [(0, 1), (0, 1), (0, 1), (0, 1), (0, 1), (0, 1), (0, 1)]
def diff_evolution():
  return differential_evolution(simulation, bounds, maxiter=20)

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
    x0 = [0.54989905, 0.47316074, 0.1467282, 0.06280517, 0.3554188, 0.01906106, 0.94033908]
    return basinhopping(simulation, x0, accept_test=mybounds)

####

result = diff_evolution()

print("Population:", population)
print("Number of days:", numberOfDays)
print("Max daily usable volume:", maximumDailyUsableVolume)
print(result.x, result.fun)