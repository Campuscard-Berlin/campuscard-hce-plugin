import type { PluginListenerHandle } from '@capacitor/core';

export interface EmulationPlugin {
  addListener(
    eventName: 'onNewData',
    listenerFunc: (data: string) => void,
  ): PluginListenerHandle;
}
