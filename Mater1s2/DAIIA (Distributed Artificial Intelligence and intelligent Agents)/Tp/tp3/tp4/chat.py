import numpy as np
import random

# Define the environment
original_matrix = np.array([
    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0],
    [1, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0],
    [1, 0, 0, 0, 0, 0, 0, 1, 0, 0],
    [1, 1, 0, 0, 0, 1, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 1],
    [0, 0, 1, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
])

num_rows, num_cols = original_matrix.shape
actions = ["up", "down", "left", "right"]
Q = np.zeros((num_rows, num_cols, len(actions)))

# Learning settings
learning_rate = 0.1
discount_factor = 0.9
epsilon_start = 1.0
epsilon_decay = 0.995
epsilon_min = 0.1
num_episodes = 1000
max_steps_per_episode = 100

# Rewards and penalties
goal_reward = 50
step_penalty = -1
invalid_move_penalty = -100
goal_visited_penalty = -10

# Translate actions to row/column movements
action_deltas = {
    "up": (-1, 0),
    "down": (1, 0),
    "left": (0, -1),
    "right": (0, 1)
}

# Check the validity of the move
def is_valid_move(row, col):
    return 0 <= row < num_rows and 0 <= col < num_cols

# Training phase
epsilon = epsilon_start
for episode in range(num_episodes):
    matrix = np.copy(original_matrix)
    visited_ones = set()
    current_row, current_col = 0, 0  # Always start from the top-left corner
    path = [(current_row, current_col)]  # To track the path

    if episode == 0:
        # Manually set the initial path for the first episode
        print("Episode 1: Following the initial zigzag path")
        for _ in range(num_rows * num_cols):
            if current_row < num_rows:
                if current_row % 2 == 0:  # Even rows: move right until the end
                    if current_col < num_cols - 1:
                        next_col = current_col + 1
                        action = "right"
                    elif current_col == num_cols - 1:  # At the end of row, move down if possible
                        next_row = current_row + 1
                        action = "down"
                else:  # Odd rows: move left until the start
                    if current_col > 0:
                        next_col = current_col - 1
                        action = "left"
                    elif current_col == 0:  # At the start of row, move down if possible
                        next_row = current_row + 1
                        action = "down"

                # Execute the action
                delta_row, delta_col = action_deltas[action]
                next_row, next_col = current_row + delta_row, current_col + delta_col
                if is_valid_move(next_row, next_col):
                    if matrix[next_row, next_col] == 1 and (next_row, next_col) not in visited_ones:
                        visited_ones.add((next_row, next_col))
                        matrix[next_row, next_col] = 0  # Clean the cell
                    current_row, current_col = next_row, next_col  # Update agent position
                    path.append((current_row, current_col))  # Track the path
    else:
        # Normal Q-learning episodes
        for step in range(max_steps_per_episode):
            if random.random() < epsilon:
                action = random.choice(actions)
            else:
                action_values = Q[current_row, current_col]
                action_index = np.argmax(action_values)
                action = actions[action_index]

            delta_row, delta_col = action_deltas[action]
            next_row, next_col = current_row + delta_row, current_col + delta_col

            if not is_valid_move(next_row, next_col):
                reward = invalid_move_penalty
            else:
                reward = step_penalty
                if matrix[next_row, next_col] == 1:
                    if (next_row, next_col) not in visited_ones:
                        reward += goal_reward
                        visited_ones.add((next_row, next_col))
                        matrix[next_row, next_col] = 0  # Clean the cell
                    else:
                        reward += goal_visited_penalty

                current_row, current_col = next_row, next_col  # Update the agent's position
                path.append((current_row, current_col))  # Track the path

                # Update Q-table
                old_value = Q[current_row, current_col, actions.index(action)]
                next_max = np.max(Q[next_row, next_col])
                Q[current_row, current_col, actions.index(action)] += learning_rate * (reward + discount_factor * next_max - old_value)

    epsilon = max(epsilon * epsilon_decay, epsilon_min)  # Epsilon decay after each episode
    if episode in [0, 999]:  # Optionally print paths for first and last episodes
        print(f"Path for episode {episode + 1}: {path}")

# Optionally, add demonstration of learned policy here
