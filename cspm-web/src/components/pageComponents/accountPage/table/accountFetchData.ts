'use client'

import {
  useExcelDownList,
  useFilter,
  useSearchStore,
  useSelectType,
  useTrigger,
} from '@/components/Zustand/store'
import { useEffect } from 'react'

interface Props {
  client: string
  accountName: string
  accountId: string
  registerTime: string
  lastUpdateDescribeTime: string
  describeResult: string
}

type SetData = (data: Props[]) => void

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL

export default function AccountFetchData(setData: SetData) {
  const { setAccountNameFilter, setClientFilter } = useFilter()
  const { setExcelList } = useExcelDownList()
  const { clientSelected, accountNameSelected } = useSelectType()
  const { baseSearcheValue } = useSearchStore()
  const { repeatFetchTrigger, setRepeatFetchTrigger, fetchTrigger } = useTrigger()
  const fetchList = {
    client: clientSelected,
    accountName: accountNameSelected,
    searchData: baseSearcheValue,
  }

  const accountDescribe = async () => {
    setExcelList(fetchList)
    try {
      const response = await fetch(`${BASE_URL}/accounts/list`, {
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

        if (inner.code === 12) {
          setData([])
          setAccountNameFilter([])
          setClientFilter([])
        }
        if (inner.code === 0) {
          const data = inner.result.data
          const accountNameListData = inner.result.accountName
          const clientListData = inner.result.client
          setData(data)
          setAccountNameFilter(accountNameListData)
          setClientFilter(clientListData)
        }
      } else {
        setData([])
        setAccountNameFilter([])
        setClientFilter([])
      }
    } catch (error) {
      console.error('오류가 발생 : ', error)
      setData([])
      setAccountNameFilter([])
      setClientFilter([])
    }
  }
  useEffect(() => {
    accountDescribe()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [clientSelected, accountNameSelected, baseSearcheValue, fetchTrigger])

  /**
   * 스캔 시작 버튼을 누른 후 30초 동안 패치를 진행하여 계속
   */

  useEffect(() => {
    if (repeatFetchTrigger === true) {
      const intervalId = setInterval(accountDescribe, 1000) // 1초마다 accountDescribe 호출
      const timeoutId = setTimeout(() => {
        clearInterval(intervalId)
        setRepeatFetchTrigger(false)
      }, 10000)
      return () => {
        clearInterval(intervalId)
        clearTimeout(timeoutId)
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [repeatFetchTrigger])
}
