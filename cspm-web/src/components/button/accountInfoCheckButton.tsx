'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'
import { useSelectType } from '@/components/Zustand/store'
import ButtonLayer from '@/components/ui/buttonLayer'
import { useState } from 'react'

const BASE_URL = process.env.NEXT_PUBLIC_NEXT_APP_BASE_URL

export default function AccountInfoCheckButton() {
  const { inputAccessKey, inputAccessSecretKey, setInputAccountId } = useInputStore()
  const { regionSelected } = useSelectType()
  const [success, setSuccess] = useState<boolean>()
  const [response, setResponse] = useState('')

  const fetchData = async () => {
    try {
      const response = await fetch(`${BASE_URL}/accounts/info`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json; charset=utf-8',
          region: encodeURIComponent(regionSelected),
          'access-key': encodeURIComponent(inputAccessKey),
          'secret-access-key': encodeURIComponent(inputAccessSecretKey),
        },
        credentials: 'include',
      })

      if (response.ok) {
        const inner = await response.json()
        setSuccess(inner.success)
        setInputAccountId(inner.id)
        setResponse('응답이 옴')
      } else {
        console.error('Failed to fetch data')
      }
    } catch (error) {
      console.error('Error fetching account info:', error)
    }
  }

  return (
    <div className='flex w-[25%] items-center gap-3 overflow-hidden'>
      <ButtonLayer buttonStyle='bg-kyboNavy' method={fetchData} childText='조회' />
      {response.length > 0 && (
        <div>{success ? <span>성공</span> : <span>실패 : 권한 및 오타 확인</span>}</div>
      )}
    </div>
  )
}
