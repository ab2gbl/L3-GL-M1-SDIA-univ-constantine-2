#2018-04-17 22:01:49
import datetime
import time

dt = datetime.datetime.strptime("2018-04-17 22:01:49", "%Y-%m-%d %H:%M:%S")

# Calculate 3 hours before
three_hours_before = dt - datetime.timedelta(hours=3)

# Open a file to write the timestamps
with open('timestamps.txt', 'w') as file:
    current_time = three_hours_before
    while current_time <= dt:  # Include the exact time "2018-04-17 22:00:18"
       
        timestamp = int(time.mktime(current_time.timetuple()))
        
        # Write the timestamp to the file using correct Python format
        file.write(f"{timestamp}\n")  # Using f-string for cleaner formatting
        
        # Increment the current_time by one minute
        current_time += datetime.timedelta(minutes=1)

print("Timestamps for every minute, 3 hours before and including '2018-04-17 22:00:18', have been written to 'timestamps.txt'.")

