package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.model.TiebreakerRule

class HardBot(
    tiebreakerRule: TiebreakerRule
): NLevelBestScoreBot(maxDepth = 4, tiebreakerRule = tiebreakerRule)