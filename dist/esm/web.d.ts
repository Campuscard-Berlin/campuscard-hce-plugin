import { WebPlugin } from '@capacitor/core';
import type { EmulationPlugin } from './definitions';
export declare class EmulationWeb extends WebPlugin implements EmulationPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
