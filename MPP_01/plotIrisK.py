import matplotlib.pyplot as plt
import pandas as pd

data = pd.read_csv('/home/kuba/Sync/NAI_MPP_01_KNN/plotData.csv', sep=';')
# print(data.head(20))

plt.plot(data['k'], data['Accuracy'])
plt.show()