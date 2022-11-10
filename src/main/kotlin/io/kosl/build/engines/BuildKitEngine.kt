package io.kosl.build.engines

import io.kosl.build.BuildEngine
import io.kosl.build.BuildEngineJob
import io.kosl.context.KoslContext
import io.kosl.execution.parameter.*

class BuildKitEngine: BuildEngine {
  override fun process(context: KoslContext, job: BuildEngineJob) {
    val command = mutableListOf(
      CommandName("buildctl"),
      SubCommandName("build"),
      RawArgument("--progress"),
      RawArgument("plain"),
      RawArgument("--frontend"),
      RawArgument("dockerfile.v0"),
      RawArgument("--local"),
      JoinedParameter(RawArgument("context="), RelativeDirectoryPath(job.contextDirectoryPath)),
      RawArgument("--local"),
      JoinedParameter(RawArgument("dockerfile="), RelativeDirectoryPath(job.contextDirectoryPath)),
      RawArgument("--opt"),
      JoinedParameter(RawArgument("source="), RelativeFilePath(job.buildFilePath)),
      RawArgument("--output"),
      RawArgument("type=image,name=${job.targetImageName}:${job.targetImageTag},push=${job.push}")
    )

    if (System.getenv("KOSL_BUILDKIT_USE_SUDO") == "true") {
      command.add(0, CommandName("sudo"))
    }

    context.executeInteractiveProcess(command)
  }
}
