import { create } from 'zustand'

type ScanStore = {
  scanClient: string[]
  scanAccountIdList: string[]
  scanAccountName: string[]
}

type ScanAction = {
  setScanClient: (clientList: string[]) => void
  setScanAccountIdList: (accountIdList: string[]) => void
  setScanAccountName: (AccountNameList: string[]) => void
  setScanScanReset: () => void
}

const initialScan: ScanStore = {
  scanClient: [],
  scanAccountIdList: [],
  scanAccountName: [],
}

export const useScanSet = create<ScanAction & ScanStore>((set) => ({
  ...initialScan,
  setScanClient: (clientList) => set((state) => ({ scanClient: clientList })),
  setScanAccountIdList: (accountIdList) => set((state) => ({ scanAccountIdList: accountIdList })),
  setScanAccountName: (ServiceList) => set((state) => ({ scanAccountName: ServiceList })),

  setScanScanReset: () => set(initialScan),
}))

type inputStore = {
  inputAccessKey: string
  inputAccountName: string
  inputAccessSecretKey: string
  inputClientValue: string
  beforeAccountName: string
  inputCode: string
  inputComment: string
  inputAccountId: string
  resultAccountName: string
}

type inputAction = {
  setInputAccessKey: (accessKeyType: string) => void
  setBeforeAccountName: (before: string) => void
  setInputAccountName: (accountNameInputType: string) => void
  setInputAccessSecretKey: (accountSecretKeyInputType: string) => void
  setInputClientValue: (clintInputtype: string) => void
  setInputCode: (codeFromInput: string) => void
  setInputComment: (commentInput: string) => void
  setResultAccountName: (resultAccountNameProps: string) => void
  setInputAccountId: (accountIdType: string) => void
  setInputReset: () => void
}

const initialInput: inputStore = {
  inputAccessKey: '',
  beforeAccountName: '',
  inputAccountName: '',
  inputAccessSecretKey: '',

  inputClientValue: '',
  inputCode: '',
  inputComment: '',
  inputAccountId: '',
  resultAccountName: '',
}

export const useInputStore = create<inputStore & inputAction>((set) => ({
  ...initialInput,
  setInputAccessKey: (accessKeyType) => set(() => ({ inputAccessKey: accessKeyType })),
  setBeforeAccountName: (before) => set(() => ({ beforeAccountName: before })),
  setInputAccountName: (aaccountNameInpytTypecc) =>
    set((state) => ({ inputAccountName: aaccountNameInpytTypecc })),
  setInputAccessSecretKey: (accountSecretKeyInputType) =>
    set((state) => ({ inputAccessSecretKey: accountSecretKeyInputType })),
  setInputClientValue: (clintInputtype) => set((state) => ({ inputClientValue: clintInputtype })),
  setInputCode: (codeFromInput) => set((state) => ({ inputCode: codeFromInput })),
  setInputComment: (commentInput) => set((state) => ({ inputComment: commentInput })),
  setResultAccountName: (resultAccountNameProps) =>
    set((state) => ({ resultAccountName: resultAccountNameProps })),
  setInputAccountId: (accountIdType) => set((state) => ({ inputAccountId: accountIdType })),
  setInputReset: () => set(initialInput),
}))

type checkoutedType = {
  selectIndex: number
  checkedItems: Set<number>
  checkedAcountIdList: Array<String>
  setCheckedItems: (items: Set<number>) => void
  setSelectIndex: (selectedIndex: number) => void
  setCheckedItemsReset: () => void
}
export const useCheckedItemsStore = create<inputStore & checkoutedType>((set) => ({
  ...initialInput,
  selectIndex: 0,
  checkedItems: new Set<number>(),
  checkedAcountIdList: [],
  setCheckedItems: (items: Set<number>) => set((state) => ({ checkedItems: items })),
  setSelectIndex: (selectedIndex: number) => set((state) => ({ selectIndex: selectedIndex })),
  setCheckedItemsReset: () => set(initialInput),
}))
