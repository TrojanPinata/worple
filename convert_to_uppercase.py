def convert_to_uppercase(input_file, output_file):
    with open(input_file, 'r') as file:
        words = file.readlines()

    uppercase_words = [word.upper() for word in words]

    with open(output_file, 'w') as file:
        file.writelines(uppercase_words)

input_file = 'input.txt'
output_file = 'output.txt'
convert_to_uppercase(input_file, output_file)
