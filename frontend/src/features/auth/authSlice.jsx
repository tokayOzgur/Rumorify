import { loadAuthState } from "@/shared/state/storage";
import { createSlice } from "@reduxjs/toolkit";

export const authSlice = createSlice({
  name: "auth",
  initialState: loadAuthState(),
  reducers: {
    loginSuccess: (state, action) => {
      state.id = action.payload.id;
      state.username = action.payload.username;
      state.email = action.payload.email;
      state.image = action.payload.image;
      console.log(state.id, state.username, state.email, state.image)
    },
    logoutSuccess: (state, action) => {
      state.id = 0;
      delete state.username;
      delete state.email;
      delete state.image;
    },
  },
});

export const { loginSuccess, logoutSuccess } = authSlice.actions;
export default authSlice.reducer;
