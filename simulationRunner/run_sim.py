import matplotlib.pyplot as plt
import time
from subprocess import check_output

drinkingPercentage = 9.924
cropPercentage = 11.99
hygienePercentage = 3.17
laundryPercentage = 54.29
flushPercentage = 0.496
medicalPercentage = 0.396
electrolysisPercentage = 19.72

population = 100
numberOfDays = 10
maximumDailyUsableVolume = 10

out = check_output(['java', '-jar', 'simulation.jar', str(drinkingPercentage), str(cropPercentage), str(hygienePercentage), str(laundryPercentage), str(flushPercentage), str(medicalPercentage), str(electrolysisPercentage), str(population), str(numberOfDays), str(maximumDailyUsableVolume)])

line = list(map(lambda x: float(x), out.split()))

plt.plot(line)
plt.show()