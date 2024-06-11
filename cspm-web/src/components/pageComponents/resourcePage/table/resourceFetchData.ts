'use client'
import {
  useCalendarPicker,
  useExcelDownList,
  useFilter,
  useSearchStore,
  useSelectType,
} from '@/components/Zustand/store'
import Format from '@/components/calendarComponents/fromat'
import { useEffect, useState } from 'react'

interface Props {
  scanTime: string
  createTime: string
  client: string
  accountName: string
  accountId: string
  serviceGroup: string
  resourceId: string
  tag: string
}

type SetData = (data: Props[]) => void

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL

export default function FetchData(setData: SetData) {
  const { baseSearcheValue } = useSearchStore()
  const { fromPickDate, toPickDate } = useCalendarPicker()
  const [fromCalendar, setFromCalender] = useState<string>('')
  const [toCalendar, setToCalender] = useState<string>('')
  const { setExcelList } = useExcelDownList()
  const { setAccountIdFilter, setAccountNameFilter, setClientFilter } = useFilter()
  const {
    clientSelected,
    accountIdSelected,
    accountNameSelected,
    serviceSelected,
    selectedResource,
  } = useSelectType()

  const fetchList = {
    client: clientSelected,
    accountName: accountNameSelected,
    accountId: accountIdSelected,
    fromDate: fromCalendar,
    toDate: toCalendar,
    searchDate: baseSearcheValue,
    service: serviceSelected,
    resource: selectedResource,
  }

  const resourceDescribe = async () => {
    setExcelList(fetchList)
    try {
      const response = await fetch(`${BASE_URL}/resources/list`, {
        method: 'POST',
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
          const accountNameListdata = inner.result.accountName
          const accountIdListData = inner.result.accountId
          const clientListData = inner.result.client
          setData(data)
          setAccountNameFilter(accountNameListdata)
          setAccountIdFilter(accountIdListData)
          setClientFilter(clientListData)
        } else if (inner.code === 12) {
          setData([])
          setExcelList({})
          setAccountNameFilter([])
          setAccountIdFilter([])
          setClientFilter([])
        }
      }
    } catch (error) {
      console.error(error)
      setData([])
      setExcelList({})
      setAccountNameFilter([])
      setAccountIdFilter([])
      setClientFilter([])
    }
  }

  useEffect(() => {
    resourceDescribe()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [
    clientSelected,
    accountIdSelected,
    accountNameSelected,
    serviceSelected,
    fromCalendar,
    toCalendar,
    baseSearcheValue,
    selectedResource,
  ])

  useEffect(() => {
    const formattedFromDate = Format(fromPickDate)
    setFromCalender(formattedFromDate)

    const formattedToDate = Format(toPickDate)
    setToCalender(formattedToDate)
  }, [fromPickDate, toPickDate])
}
