import { WebPlugin } from '@capacitor/core';
export class EmulationWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}
//# sourceMappingURL=web.js.map