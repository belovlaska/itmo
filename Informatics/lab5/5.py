import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv("dop3_1.csv", delimiter=";")

df.boxplot()
plt.xticks(rotation=90, fontsize=5)
plt.show()