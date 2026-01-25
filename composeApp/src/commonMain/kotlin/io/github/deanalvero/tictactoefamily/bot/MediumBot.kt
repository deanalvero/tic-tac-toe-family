package io.github.deanalvero.tictactoefamily.bot

import io.github.deanalvero.tictactoefamily.model.TiebreakerRule

class MediumBot(
    tiebreakerRule: TiebreakerRule
): NLevelBestScoreBot(maxDepth = 2, tiebreakerRule = tiebreakerRule)