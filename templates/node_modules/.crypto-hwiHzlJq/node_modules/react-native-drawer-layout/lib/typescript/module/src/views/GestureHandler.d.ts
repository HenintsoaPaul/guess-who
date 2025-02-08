import * as React from 'react';
import { View } from 'react-native';
import type { GestureType } from 'react-native-gesture-handler';
type GestureDetectorProps = {
    gesture: GestureType | undefined;
    userSelect?: 'none' | 'auto' | 'text';
    children: React.ReactNode;
};
export declare const GestureDetector: ({ gesture: _0, userSelect: _1, children, }: GestureDetectorProps) => import("react/jsx-runtime").JSX.Element;
export declare const GestureHandlerRootView: typeof View;
export declare const Gesture: typeof import('react-native-gesture-handler').Gesture | undefined;
export declare const enum GestureState {
    UNDETERMINED = 0,
    FAILED = 1,
    BEGAN = 2,
    CANCELLED = 3,
    ACTIVE = 4,
    END = 5
}
export {};
//# sourceMappingURL=GestureHandler.d.ts.map