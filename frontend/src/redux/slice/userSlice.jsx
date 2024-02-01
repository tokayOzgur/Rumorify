import { createSlice } from "@reduxjs/toolkit";

export const initialState = {
  users: [],
  status: "idle",
  error: null,
};

export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUserDetail: (state, action) => {
      state.users = action.payload;
    },
  },
});

export const { setUserDetail } = userSlice.actions;
export default userSlice.reducer;
