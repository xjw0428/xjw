#coding:utf-8
import random
import math

flow_num = 3
path_num = 5

y_init = math.ceil((10*random.random())%path_num)

maxgen = 200  #迭代次数
subsizepop = 20  #种群规模
x=list(range(subsizepop));y=list(range(subsizepop))
D=list(range(subsizepop));S=list(range(subsizepop))
Smell=list(range(subsizepop));P=list(range(subsizepop))
yy=list(range(maxgen+1))

#P=[[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]]
P = [[0] * flow_num for i in range(subsizepop)] #subsizepop行flow_num列的矩阵
#果蝇寻优开始，利用嗅觉寻找食物
for i in range(subsizepop):
    Ds=0
    for j in range(flow_num):
       x[j]=j+1
       y[j]=math.ceil((y_init+10*random.random())%path_num)
       #print(y[j])
       D[j]=(x[j]**2+y[j]**2)**(0.5)
       Ds += D[j]
       P[i][j]=y[j]

    D[i]=1/flow_num*Ds
    S[i]=1/D[i]
    Smell[i]=(S[i]-0.1)**2

#找出此果蝇群体的中味道浓度最高的果蝇(求极大值)
bestSmell=min(Smell)
bestindex=Smell.index(bestSmell)
#保留最佳味道浓度值与 P子群坐标，此时果蝇群体利用视觉往该位置飞去。
P_init=P[bestindex]
Smellbest=bestSmell

#果蝇迭代寻优开始
for g in range(maxgen+1):
      #附与果蝇个体利用嗅觉搜寻食物之随机方向与距离
      for i in range(subsizepop):
          Ds = 0
          for j in range(flow_num):
              x[j] = j+1
              y[j] = math.ceil((P_init[j] + 10 * random.random()) % path_num)
              D[j] = (x[j] ** 2 + y[j] ** 2) ** (0.5)
              Ds += D[j]
              P[i][j]=y[j]

    #由于无法得知食物位置，因此先估计与原点之距离(Dist)，
    # 再计算味道浓度判定值(S)，此值为距离之倒数
          D[i]=1/flow_num*Ds
          S[i]=1/D[i]
    #判定值(S)代入判定函数以求出该果蝇位置的味道浓度(Smelli)
          Smell[i]=(S[i]-0.1)**2
    #找出此果蝇群体的中味道浓度最高的果蝇(求极大值)
      bestSmell=min(Smell)
      bestindex=Smell.index(bestSmell)
    #判断味道浓度是否优于前一迭代味道浓度，
    # 若是则保留最佳味道浓度值与 x、y 坐标，此时果蝇群体利用视觉往该位置飞去。
      if bestSmell<Smellbest:
        P_best=P[bestindex]
        Smellbest=bestSmell
#每代最优 Smell 值纪录到 yy 数组中，并记录最优迭代坐标
      yy[g]=Smellbest
     # X_best[g]=x_init;
      #Y_best[g]=y_init

print("经过果蝇算法优化后的最优结果：")
print (Smellbest)
print(P_best)