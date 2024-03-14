import { setToken } from "@/api/axiosInstance";
import { loadAuthState } from "@/shared/state/storage";
import { createSlice } from "@reduxjs/toolkit";

export const authSlice = createSlice({
  name: "auth",
  initialState: loadAuthState(),
  reducers: {
    loginSuccess: (state, action) => {
      setToken(action.payload.token);
      state.id = action.payload.user.id;
      state.username = action.payload.user.username;
      state.email = action.payload.user.email;
      state.image = action.payload.user.image;
    },
    logoutSuccess: (state, action) => {
      setToken(null);
      state.id = 0;
      delete state.username;
      delete state.email;
      delete state.image;
    },
  },
});

export const { loginSuccess, logoutSuccess } = authSlice.actions;
export default authSlice.reducer;
