'use client'
import {
  useCalendarPicker,
  useExcelDownList,
  useFilter,
  useSearchStore,
  useSelectType,
  useTrigger,
} from '@/components/Zustand/store'
import Format from '@/components/calendarComponents/fromat'
import { useEffect, useState } from 'react'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL

interface Props {
  scanTime: string
  rid: string
  vulnerabilityStatus: string
  accountName: string
  accountId: string
  resourceId: string
  client: string
  policySeverity: string
  policyTitle: string
  policyCompliance: string
}

type setData = (data: Props[]) => void

export default function ScanResultFetchData(setData: setData) {
  const {
    clientSelected,
    accountNameSelected,
    accountIdSelected,
    policySeveritySelected,
    vulnerabilityStatusSelected,
  } = useSelectType()

  const { fromPickDate, toPickDate } = useCalendarPicker()
  const { baseSearcheValue } = useSearchStore()
  const [fromCalendar, setFromCalender] = useState<string>('')
  const [toCalendar, setToCalender] = useState<string>('')
  const { setExcelList } = useExcelDownList()
  const { setAccountIdFilter, setAccountNameFilter, setClientFilter } = useFilter()
  const { fetchTrigger } = useTrigger()

  const scanResultDescribe = async () => {
    setExcelList(fetchList)
    try {
      const response = await fetch(`${BASE_URL}/compliances/list`, {
        method: 'POST',
        cache: 'force-cache',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
        },
        credentials: 'include',
        body: JSON.stringify(fetchList),
      })

      if (response.ok) {
        const inner = await response.json()
        if (inner.code === 0) {
          const data = inner.result.data
          const accountIdListData = inner.result.accountId
          const accountNameListData = inner.result.accountName
          const clientListData = inner.result.client
          setData(data)
          setAccountIdFilter(accountIdListData)
          setAccountNameFilter(accountNameListData)
          setClientFilter(clientListData)
        }

        if (inner.code === 12) {
          setData([])
          setExcelList({})
          setAccountIdFilter([])
          setAccountNameFilter([])
          setClientFilter([])
        }
      }
    } catch (error) {
      console.error('오류가 발생 : ', error)
      setData([])
      setExcelList({})
      setAccountIdFilter([])
      setAccountNameFilter([])
      setClientFilter([])
    }
  }

  const fetchList = {
    client: clientSelected,
    accountName: accountNameSelected,
    accountId: accountIdSelected,
    searchData: baseSearcheValue,
    fromDate: fromCalendar,
    toDate: toCalendar,
    policySeverity: policySeveritySelected,
    vulnerabilityStatus: vulnerabilityStatusSelected,
  }

  useEffect(() => {
    const fromatFromatDate = Format(fromPickDate)
    setFromCalender(fromatFromatDate)

    const toFromatDate = Format(toPickDate)
    setToCalender(toFromatDate)
  }, [fromPickDate, toPickDate])

  useEffect(() => {
    scanResultDescribe()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [
    fetchTrigger,
    clientSelected,
    accountNameSelected,
    accountIdSelected,
    policySeveritySelected,
    vulnerabilityStatusSelected,
    baseSearcheValue,
    fromCalendar,
    toCalendar,
  ])

  /**
   * 받아온 날짜를 string 및 yyyy-MM-dd HH:mm 형식으로 변경
   */
  useEffect(() => {
    const fromatFromatDate = Format(fromPickDate)
    setFromCalender(fromatFromatDate)

    const toFromatDate = Format(toPickDate)
    setToCalender(toFromatDate)
  }, [fromPickDate, toPickDate])
}
