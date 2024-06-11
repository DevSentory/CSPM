import { create } from 'zustand'

type scanResultExceptionStore = {
  accountIdException: string
  resourceIdException: string
  policyTitleException: string
  accountNameException: string
}

type scanResultExceptionAction = {
  setAccountIdException: (accountId: string) => void
  setReousrceIdException: (resourceId: string) => void
  setPolicyTitleException: (policyTitle: string) => void
  setAccountNameException: (accountName: string) => void
  setReset: () => void
}

const initialScanResultException: scanResultExceptionStore = {
  accountIdException: '',
  resourceIdException: '',
  policyTitleException: '',
  accountNameException: '',
}

export const useScanResultException = create<scanResultExceptionStore & scanResultExceptionAction>(
  (set) => ({
    ...initialScanResultException,
    setAccountIdException: (accountId) => set((state) => ({ accountIdException: accountId })),
    setReousrceIdException: (resourceId) => set((state) => ({ resourceIdException: resourceId })),
    setPolicyTitleException: (policyTitle) =>
      set((state) => ({ policyTitleException: policyTitle })),
    setAccountNameException: (accountName) =>
      set((state) => ({ accountNameException: accountName })),
    setReset: () => set(initialScanResultException),
  }),
)
