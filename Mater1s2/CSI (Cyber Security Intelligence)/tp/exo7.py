from Crypto.Cipher import AES

# Conversion des hex en bytes
plaintext = bytes.fromhex('255044462d312e350a25d0d4c5d80a34')
ciphertext = bytes.fromhex('d06bf9d0dab8e8ef880660d2af65aa82')
iv = bytes.fromhex('09080706050403020100A2B2C2D2E2F2')

# Fonction pour essayer de déchiffrer avec une clé donnée
def try_decrypt(ciphertext, key, iv):
    cipher = AES.new(key, AES.MODE_CBC, iv)
    decrypted = cipher.decrypt(ciphertext)
    return decrypted

# Lecture des clés potentielles depuis le fichier
with open('keys.txt', 'r') as file:
    hex_keys = file.readlines()

# Test des clés lues du fichier
for hex_key in hex_keys:
    # Split the line into timestamp and hex key, then strip any whitespace
    date,key_hex = hex_key.split(',')
    key_hex = key_hex.strip()
    key = bytes.fromhex(key_hex)
    decrypted = try_decrypt(ciphertext, key, iv)
    print(decrypted)
    print ( plaintext )
    if decrypted == plaintext:
        print(f'Found key: {key.hex()}, timestamp: {date}')
        break
    else:
        
        print(f'Key {key.hex()} did not work.')
