import type { PluginListenerHandle } from '@capacitor/core';

export interface EmulationPlugin {
  changeAppData(options: {lastDigit: number}): Promise<void>;
  addListener(
    eventName: 'onNewData',
    listenerFunc: (data: string) => void,
  ): PluginListenerHandle;
}
