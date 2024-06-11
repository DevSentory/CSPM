import { create } from 'zustand'

type calendarAction = {
  setFromPickDate: (fromDate: Date | null) => void
  setToPickDate: (toDate: Date | null) => void
  setCalendarReset: () => void
}

type calendarState = {
  fromPickDate: Date | null
  toPickDate: Date | null
}

const initialCalendar: calendarState = {
  fromPickDate: new Date(new Date().getTime() - 30 * 24 * 60 * 60 * 1000),
  toPickDate: new Date(),
}

export const useCalendarPicker = create<calendarState & calendarAction>((set) => ({
  ...initialCalendar,
  setFromPickDate: (fromDate) => set((state) => ({ fromPickDate: fromDate })),
  setToPickDate: (toDate) => set((state) => ({ toPickDate: toDate })),
  setCalendarReset: () =>
    set((state) => ({
      fromPickDate: new Date(new Date().getTime() - 30 * 24 * 60 * 60 * 1000),
      toPickDate: new Date(),
    })),
}))

type selectState = {
  accountIdSelected: string
  accountNameSelected: string
  clientSelected: string
  serviceSelected: string
  policySeveritySelected: string
  vulnerabilityStatusSelected: string
  regionSelected: string
  selectedResource: string
}

type selectAction = {
  setRegionSelected: (selectResion: string) => void
  setAccountIdSelected: (selectType: string) => void
  setAccountNameSelected: (selectName: string) => void
  setClientSelected: (selectClient: string) => void
  setServiceSelected: (selectService: string) => void
  setPolicySeveritySelected: (selectWeakRating: string) => void
  setVulnerabilityStatusSelected: (selectWeakState: string) => void
  setSelectedResource: (selectResorce: string) => void
  setSelectReset: () => void
}

const initialSelect: selectState = {
  accountIdSelected: '',
  accountNameSelected: '',
  clientSelected: '',
  serviceSelected: '',
  policySeveritySelected: '',
  vulnerabilityStatusSelected: '',
  regionSelected: 'ap-northeast-2',
  selectedResource: '',
}

export const useSelectType = create<selectState & selectAction>((set) => ({
  ...initialSelect,
  setRegionSelected: (selectResion) => set((state) => ({ regionSelected: selectResion })),
  setAccountIdSelected: (selectType) => set((state) => ({ accountIdSelected: selectType })),
  setAccountNameSelected: (selectName) => set((state) => ({ accountNameSelected: selectName })),
  setClientSelected: (selectClient) => set((state) => ({ clientSelected: selectClient })),
  setServiceSelected: (selectService) => set((state) => ({ serviceSelected: selectService })),
  setPolicySeveritySelected: (selectWeakRating) =>
    set((state) => ({ policySeveritySelected: selectWeakRating })),
  setVulnerabilityStatusSelected: (selectWeakState) =>
    set((state) => ({ vulnerabilityStatusSelected: selectWeakState })),
  setSelectedResource: (selectResorce) => set((state) => ({ selectedResource: selectResorce })),
  setSelectReset: () => set(initialSelect),
}))

type ExcelDownListState = {
  excelList: object
}

type ExcelDownListActions = {
  setExcelList: (excelList: object) => void
}

const initialExcelDownList: ExcelDownListState = {
  excelList: {},
}

export const useExcelDownList = create<ExcelDownListState & ExcelDownListActions>((set) => ({
  ...initialExcelDownList,
  setExcelList: (excelLists) => set(() => ({ excelList: excelLists })),
}))

type searchStore = {
  baseSearcheValue: string
}

type searchAction = {
  setBaseSearcheValue: (baseSearch: string) => void
  setSearchReset: () => void
}

const initialSearch: searchStore = {
  baseSearcheValue: '',
}

export const useSearchStore = create<searchStore & searchAction>((set) => ({
  ...initialSearch,
  setBaseSearcheValue: (baseSearch) => set((state) => ({ baseSearcheValue: baseSearch })),
  setSearchReset: () => set(initialSearch),
}))

type resetAction = {
  setRequestMeta: () => void
}
export const useMetaResuet = create<resetAction>((get) => ({
  setRequestMeta: () => {
    useSearchStore.getState().setSearchReset()
    useSelectType.getState().setSelectReset()
    useCalendarPicker.getState().setCalendarReset()
  },
}))

type filterStore = {
  clientFilter: string[]
  accountIdFilter: string[]
  accountNameFilter: string[]
}

type filterAction = {
  setClientFilter: (clientList: string[]) => void
  setAccountIdFilter: (accountIdList: string[]) => void
  setAccountNameFilter: (accountNameList: string[]) => void
}

const initialFilter: filterStore = {
  clientFilter: [],
  accountIdFilter: [],
  accountNameFilter: [],
}

export const useFilter = create<filterAction & filterStore>((set) => ({
  ...initialFilter,
  setClientFilter: (clientList) => set((state) => ({ clientFilter: clientList })),
  setAccountIdFilter: (accountIdList) => set((state) => ({ accountIdFilter: accountIdList })),
  setAccountNameFilter: (accountNameList) =>
    set((state) => ({ accountNameFilter: accountNameList })),
}))

type trigger = {
  fetchTrigger: boolean
  repeatFetchTrigger: boolean
  setFetchTrigger: () => void
  setRepeatFetchTrigger: (setBoolean: boolean) => void
}
export const useTrigger = create<trigger>((set) => ({
  fetchTrigger: false,
  repeatFetchTrigger: false,
  setFetchTrigger: () => set((state) => ({ fetchTrigger: !state.fetchTrigger })),
  setRepeatFetchTrigger: (setBoolean) => set((state) => ({ repeatFetchTrigger: setBoolean })),
}))
