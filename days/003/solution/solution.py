from functools import reduce
from operator import add
import re


pattern = r"(do\(\))|(don't\(\))|mul\((\d+),(\d+)\)"


def toggle(iterable, initial=None):
    total = initial
    it = iter(iterable)
    for item in it:
        total = total + item
        if total > 1:
            total = 1
        elif total < 0:
            total = 0
        yield total


with open("input", "r") as file:
    print(
        reduce(
            add,
            map(
                lambda x: int(x[2]) * int(x[3]) if x[2] and x[3] else 0,
                re.findall(pattern, file.read())
            ),
            0
        )
    )

    file.seek(0)

    instructions = re.findall(pattern, file.read())

    enabled = True
    results = []

    for instr in instructions:
        if instr[0]:
            enabled = True
        if instr[1]:
            enabled = False

        if enabled and instr[2] and instr[3]:
            results.append(int(instr[2]) * int(instr[3]))

    print(sum(results))
