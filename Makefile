.PHONY: readme.md.template

readme.md: readme.md.template
	cat readme.md.template > readme.md
	ls days | \
		xargs -L1 -I {} \
		echo "| {} | [prompt](https://github.com/eliseomartelli/aoc2024/tree/main/{}/prompt)| [solution](https://github.com/eliseomartelli/aoc2024/tree/main/{}/solution) |" \
		>> readme.md


