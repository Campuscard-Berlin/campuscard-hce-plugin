import { registerPlugin } from '@capacitor/core';

import type { EmulationPlugin } from './definitions';

const Emulation = registerPlugin<EmulationPlugin>('Emulation', {
  web: () => import('./web').then(m => new m.EmulationWeb()),
});

export * from './definitions';
export { Emulation };
