import matplotlib.pyplot as plt
import matplotlib.patches as mpatches
import time
import numpy as np
from subprocess import check_output

population = 100
numberOfDays = 20
maximumDailyUsableVolume = 6

# strategy [drinkingWeightage, cropWeightage, hygieneWeightage, laundryWeightage, flushWeightage, medicalWeightage, electrolysisWeightage]
original = [11.99, 9.924, 3.17, 54.29, 0.496, 0.396, 19.72]
strategy1 = [11.99, 9.924, 3.17, 44.29, 0.496, 0.396, 29.72]
strategy2 = [10.503995, 8.68848, 2.7751, 47.527, 0.43442, 0.34689, 29.72]
optimal_strat = [0.92217639, 0.59109772, 0.2457574, 0.00906157, 0.01010687, 0.08193237, 0.98333903]
pandemic_optimal = [0.98959778, 0.85060721, 0.35725193, 0.00211098, 0.02511889, 0.2084208, 0.95087705]

def runSimulation(x):
    out = check_output(['java', '-jar', 'pandemic_nondeterministic.jar', str(x[0]), str(x[1]), str(x[2]), str(x[3]), str(x[4]), str(x[5]), str(x[6]), str(population), str(numberOfDays), str(maximumDailyUsableVolume)])

    data = list(map(lambda x: float(x), out.split()))
    first_zero = next((i for i, x in enumerate(data) if x < 0.000001), 0)

    # print("Population:", population)
    # print("Number of days:", numberOfDays)
    # print("Max daily usable volume:", maximumDailyUsableVolume)
    # print("First day SOL reaches 0:", first_zero / 24)

    return np.array(data)

# plt.plot(runSimulation(optimal))
# plt.title("Graph of Standard Of Living over Time (Unoptimised)")
# plt.ylabel("Standard Of Living")
# plt.xlabel("Time in hours")
# plt.xlim(0, numberOfDays * 24 + 1)
# plt.ylim(0, 11)
# plt.show()

# Print multiple runs
suboptimal = []
optimal = []

for i in range(0, 5):
    dataOptimal = runSimulation(optimal_strat)
    dataSuboptimal = runSimulation(pandemic_optimal)

    optimal.append(dataOptimal.copy())
    suboptimal.append(dataSuboptimal.copy())
    plt.plot(dataOptimal, color='lime', alpha=0.2)
    plt.plot(dataSuboptimal, color='orangered', alpha=0.2)

red_patch = mpatches.Patch(color='orangered', label='Pandemic optimal allocation')
green_patch = mpatches.Patch(color='lime', label='Pre-pandemic optimal allocation')
plt.legend(handles=[red_patch, green_patch])
plt.title("Graph of Standard Of Living over Time, 5 simulations per allocation.")
plt.ylabel("Standard Of Living")
plt.xlabel("Time in hours")
plt.xlim(0, numberOfDays * 24 + 1)
plt.ylim(0, 11)
plt.show()