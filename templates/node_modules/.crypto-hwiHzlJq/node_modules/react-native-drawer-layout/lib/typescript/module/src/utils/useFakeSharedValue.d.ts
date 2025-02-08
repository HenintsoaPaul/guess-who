declare class FakeSharedValue {
    _listeners: Map<number, (value: number) => void>;
    _value: number;
    constructor(value: number);
    addListener(id: number, listener: (value: number) => void): void;
    removeListener(id: number): void;
    modify(modifier?: (value: number) => number): void;
    get(): number;
    set(value: number): void;
    set value(value: number);
    get value(): number;
    _isReanimatedSharedValue: boolean;
}
/**
 * Compatibility layer for `useDrawerProgress` with `react-native-reanimated`
 */
export declare function useFakeSharedValue(value: number): FakeSharedValue;
export {};
//# sourceMappingURL=useFakeSharedValue.d.ts.map