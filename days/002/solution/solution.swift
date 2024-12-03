import Foundation

func isSafeReport(_ levels: [Int]) -> Bool {
    let isAscending = levels[1] - levels[0] > 0
    for i in 0..<levels.count - 1 {
        let diff = abs(levels[i + 1] - levels[i])
        if diff < 1 || diff > 3 {
            return false
        }
        if (!isAscending && levels[i] < levels[i + 1])
            || (isAscending && levels[i] > levels[i + 1])
        {
            return false
        }
    }
    return true
}

func isSafeWithDampener(_ levels: [Int]) -> Bool {
    if isSafeReport(levels) {
        return true
    }

    for i in 0..<levels.count {
        var modifiedLevels = levels
        modifiedLevels.remove(at: i)
        if isSafeReport(modifiedLevels) {
            return true
        }
    }

    return false
}

func countSafeReports(fromFile filePath: String, useDampener: Bool) -> Int? {
    do {
        let fileContent = try String(contentsOfFile: filePath, encoding: .utf8)
        let reports = fileContent.split(separator: "\n").map { String($0) }
        var safeReportCount = 0
        for report in reports {
            let levels = report.split(separator: " ").compactMap { Int($0) }
            if useDampener ? isSafeWithDampener(levels) : isSafeReport(levels) {
                safeReportCount += 1
            }
        }
        return safeReportCount
    } catch {
        print("\(error)")
        return 0
    }
}

let filePath = "input"
print("\(countSafeReports(fromFile: filePath, useDampener: false)!)")
print("\(countSafeReports(fromFile: filePath, useDampener: true)!)")
