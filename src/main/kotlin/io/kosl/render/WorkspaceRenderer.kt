package io.kosl.render

import io.kosl.spec.ServiceSpec
import io.kosl.spec.WorkspaceSpec
import io.kosl.state.ServiceState
import io.kosl.state.WorkspaceState
import io.kosl.context.KoslContext

class WorkspaceRenderer(val context: KoslContext) {
  fun render(spec: WorkspaceSpec): WorkspaceState {
    val serviceStates = mutableListOf<ServiceState>()
    for (serviceName in spec.services) {
      val serviceDirectoryPath = context.workspaceDirectoryPath.resolve(serviceName)
      val serviceSpecPath = serviceDirectoryPath.resolve("kosl-service.json")
      val service = ServiceSpec.loadFromPath(serviceSpecPath)
      if (service.disabled) {
        continue
      }
      val serviceState = ServiceState(context, service, serviceDirectoryPath)
      serviceStates.add(serviceState)
    }
    return WorkspaceState(
      context,
      spec,
      serviceStates,
      context.overrideImagePrefix ?: spec.defaultImagePrefix
    )
  }
}
