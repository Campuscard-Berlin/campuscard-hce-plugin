import type { PluginListenerHandle } from '@capacitor/core';

export interface EmulationPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  addListener(
    eventName: 'onNewData',
    listenerFunc: (data: string) => void,
  ): PluginListenerHandle;
}
