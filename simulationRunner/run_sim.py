import matplotlib.pyplot as plt
import time
from subprocess import check_output

drinkingWeightage = 11.99
cropWeightage = 9.924
hygieneWeightage = 3.17
laundryWeightage = 54.29
flushWeightage = 0.496
medicalWeightage = 0.396
electrolysisWeightage = 19.72

population = 100
numberOfDays = 100
maximumDailyUsableVolume = 9

t1 = time.time()
out = check_output(['java', '-jar', 'simulation.jar', str(drinkingWeightage), str(cropWeightage), str(hygieneWeightage), str(laundryWeightage), str(flushWeightage), str(medicalWeightage), str(electrolysisWeightage), str(population), str(numberOfDays), str(maximumDailyUsableVolume)])
t2 = time.time()

line = list(map(lambda x: float(x), out.split()))

print("Population:", population)
print("Number of days:", numberOfDays)
print("Max daily usable volume:", maximumDailyUsableVolume)
print("Time taken:", t2 - t1)
print(line[-1])

plt.plot(line)
plt.title("Graph of Standard Of Living over Time")
plt.ylabel("Standard Of Living")
plt.xlabel("Time in hours")
plt.xlim(0, numberOfDays * 24 + 1)
plt.ylim(0, 11)
plt.show()