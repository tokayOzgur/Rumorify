import { storeAuthState } from "@/shared/state/storage";
import { configureStore } from "@reduxjs/toolkit";
import authReducer from "@/features/auth/authSlice";

export const store = configureStore({
  reducer: {
    auth: authReducer,
  },
});

store.subscribe(() => {
  storeAuthState(store.getState().auth);
});
