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
    print(
        sum(
            int(x[2]) * int(x[3]) * e if x[2] and x[3] else 0
            for x, e in zip(
                instructions,
                toggle(
                    (
                        1
                        if instr[0]
                        else -1
                        if instr[1]
                        else 0
                        for instr in instructions
                    ),
                    initial=1
                )
            )
        )
    )
