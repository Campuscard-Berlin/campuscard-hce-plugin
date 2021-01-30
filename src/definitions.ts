export interface EmulationPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
