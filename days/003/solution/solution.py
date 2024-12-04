from functools import reduce
from operator import add
import re


pattern = r"(do\(\))|(don't\(\))|mul\((\d+),(\d+)\)"


def toggle(iterable, initial=None):
    total = initial
    for item in iterable:
        total += item
        total = max(0, min(1, total))
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
        reduce(
            add,
            map(
                lambda x:
                    int(x[0][2]) * int(x[0][3]) * x[1]
                    if x[0][2] and x[0][3] else 0,
                zip(
                    instructions,
                    toggle(
                        (
                            1 if instr[0] else -1 if instr[1] else 0
                            for instr in instructions
                        ),
                        initial=1,
                    )
                    )
            )
        )
    )
