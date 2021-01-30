import { registerPlugin } from '@capacitor/core';
const Emulation = registerPlugin('Emulation', {
    web: () => import('./web').then(m => new m.EmulationWeb()),
});
export * from './definitions';
export { Emulation };
//# sourceMappingURL=index.js.map