var capacitorEmulation = (function (exports, core) {
    'use strict';

    const Emulation = core.registerPlugin('Emulation', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.EmulationWeb()),
    });

    class EmulationWeb extends core.WebPlugin {
        changeAppData(_options) {
            return new Promise((_resolve, reject) => reject("Not implemented"));
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        EmulationWeb: EmulationWeb
    });

    exports.Emulation = Emulation;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

}({}, capacitorExports));
//# sourceMappingURL=plugin.js.map
