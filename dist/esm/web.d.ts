import { WebPlugin } from '@capacitor/core';
import type { EmulationPlugin } from './definitions';
export declare class EmulationWeb extends WebPlugin implements EmulationPlugin {
    changeAppData(_options: {
        lastDigit: number;
    }): Promise<void>;
}
