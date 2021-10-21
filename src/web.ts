import { WebPlugin } from '@capacitor/core';

import type { EmulationPlugin } from './definitions';

export class EmulationWeb extends WebPlugin implements EmulationPlugin {
  changeAppData(_options: {lastDigit: number}): Promise<void> {
    return new Promise((_resolve, reject) => reject("Not implemented"));
  }
}
