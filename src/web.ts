import { WebPlugin } from '@capacitor/core';

import type { EmulationPlugin } from './definitions';

export class EmulationWeb extends WebPlugin implements EmulationPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
