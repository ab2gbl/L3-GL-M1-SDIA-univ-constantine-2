import hashlib
from datetime import datetime, timedelta
import random

# Helper function to generate key
def generate_key(seed):
    random.seed(seed)
    return ''.join(f"{random.randint(0, 255):02x}" for _ in range(16))

# Start and end times
end_time = datetime(2018, 4, 17, 22, 1, 49)
start_time = end_time - timedelta(days=1)

# Search for the hash
target_hash = '95fa2030e73ed3f8da761b4eb805dfd7'
for dt in (start_time + timedelta(seconds=s) for s in range((24 * 3600) + 1)):
    seed = int(dt.timestamp())
    key = generate_key(seed)
    hash_result = hashlib.md5(key.encode()).hexdigest()
    if hash_result == target_hash:
        print(f"Match found: {dt}")
        break
