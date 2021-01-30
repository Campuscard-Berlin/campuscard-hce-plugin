'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const Emulation = core.registerPlugin('Emulation', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.EmulationWeb()),
});

class EmulationWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    EmulationWeb: EmulationWeb
});

exports.Emulation = Emulation;
//# sourceMappingURL=plugin.cjs.js.map
