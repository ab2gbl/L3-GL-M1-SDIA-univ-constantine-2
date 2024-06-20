# Question 01:
print("# Question 01:")
    
def fractional_knapsack(items, knapsack_capacity):
    items_ratio = []
    for item in items:
        ratio = item[1] / item[0]
        items_ratio.append((ratio, item))

    items_ratio.sort(reverse=True, key=lambda x: x[0])
    
    total_value = 0
    knapsack = []

    for ratio, item in items_ratio:
        if knapsack_capacity >= item[0]:
            knapsack.append(item)
            total_value += item[1]
            knapsack_capacity -= item[0]
        else:
            fraction = knapsack_capacity / item[0]
            partial_value = fraction * item[1]
            knapsack.append([item[0] * fraction, partial_value])
            total_value += partial_value
            break

    return knapsack, total_value

items = [
    [2, 40],
    [5, 30],
    [10, 50],
    [5, 10],
    [7, 70],
    [3, 15],
    [2, 60],
    [4, 80],
    [9, 20],
    [6, 50]
]
knapsack_capacity = 15

selected_items, total_value = fractional_knapsack(items, knapsack_capacity)

print("Selected items in the knapsack:")
for item in selected_items:
    print(f"[{item[0]}, {item[1]}]")
print("Total value in the knapsack:", total_value)

# Question 02:
print("# Question 02:")
    
def knapsack_01(items, knapsack_capacity):
    n = len(items)
    dp = []
    for i in range(n + 1):
        dp.append([0] * (knapsack_capacity + 1))
    
    for i in range(1, n + 1): 
        weight, value = items[i - 1]
        for w in range(knapsack_capacity + 1):
            if weight > w:
                dp[i][w] = dp[i - 1][w]
            else:
                dp[i][w] = max(dp[i - 1][w], dp[i - 1][w - weight] + value)

    #print(dp)
    selected_items = []
    w = knapsack_capacity
    for i in range(n, 0, -1):
        if dp[i][w] != dp[i - 1][w]:
            selected_items.append(items[i - 1])
            w -= items[i - 1][0]
    total_value=dp[n][knapsack_capacity]
    return selected_items, total_value

items = [
    [2, 40],
    [5, 30],
    [10, 50],
    [5, 10],
    [7, 70],
    [3, 15],
    [2, 60],
    [4, 80],
    [9, 20],
    [6, 50]
]
knapsack_capacity = 15

selected_items, total_value = knapsack_01(items, knapsack_capacity)

print("Selected items in the 0/1 knapsack:")
for item in selected_items:
    print(f"[{item[0]}, {item[1]}]")
print("Total value in the knapsack:", total_value)

# Question 03:
print("# Question 03:")

def multiple_knapsack_greedy(items, knapsack_capacities):
    m = len(knapsack_capacities)

    items.sort(key=lambda x: x[1] / x[0], reverse=True)

    selected_items = [[] for _ in range(m)]
    remaining_capacities = knapsack_capacities.copy()

    for item in items:
        weight, profit = item

        knapsack_index = -1
        max_utility = 0

        for i in range(m):
            if weight <= remaining_capacities[i]:
                utility = profit
                if utility > max_utility:
                    knapsack_index = i
                    max_utility = utility

        if knapsack_index != -1:
            selected_items[knapsack_index].append(item)
            remaining_capacities[knapsack_index] -= weight

    total_profit = [sum(item[1] for item in knapsack) for knapsack in selected_items]

    return selected_items, total_profit

items = [
    [2, 40],
    [5, 30],
    [10, 50],
    [5, 10],
    [7, 70],
    [3, 15],
    [2, 60],
    [4, 80],
    [9, 20],
    [6, 50]
]

knapsack_capacities = [10, 15]  
selected_items, total_profit = multiple_knapsack_greedy(items, knapsack_capacities)

for i, knapsack in enumerate(selected_items):
    print(f"Selected items in Knapsack {i + 1}:")
    for item in knapsack:
        print(f"[{item[0]}, {item[1]}]")
    print(f"Total profit in Knapsack {i + 1}: {total_profit[i]}")
