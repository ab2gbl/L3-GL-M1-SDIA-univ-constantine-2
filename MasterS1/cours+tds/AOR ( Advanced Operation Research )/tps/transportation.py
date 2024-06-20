# proposed by MOHAMED ACHRAF DAMOUS <achraf.damous@univ-constantine2.dz>

import numpy as np
from pyomo.environ import *


# Create a Concrete Model
model = ConcreteModel()
nvar=6

# Define the variables
I =RangeSet(0,nvar-1) #RangeSet type of pyomo
model.x=Var(I,domain=Integers,bounds=(0,1000))

# Coefficients for the objective function (c)
c = np.array([3000, 2400, 5000, 5000, 2400, 1300])

# Coefficients matrix for the constraints (A)
A = np.array([[1, 1, 1, 0, 0, 0],
              [0, 0, 0, 1, 1, 1],
              [1, 0, 0, 1, 0, 0],
              [0, 1, 0, 0, 1, 0],
              [0, 0, 1, 0, 0, 1]])

# Right-hand side vector for the constraints (b)
b = np.array([350, 600, 325, 300, 275])

# Define constraints
model.const=ConstraintList()
for j in range(A.shape[0]):
    model.const.add(sum(model.x[i]*A[j,i] for i in range(nvar)) <=b[j] if j<=1 else sum(model.x[i]*A[j,i] for i in range(nvar)) >=b[j])
     

# Define the objective function ()
model.fobj=Objective(expr=sum(model.x[i]*c[i]
   for i in range(nvar)),sense=minimize)

# Create a solver
solver = SolverFactory('glpk')

# Solve the model
results = solver.solve(model)

# Display the results
for i in range(nvar):
    print(f"x{i+1}=",model.x[i].value,'item')

print("objective value=",value(model.fobj), 'DA')