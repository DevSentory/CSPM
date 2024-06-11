'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'
import { useEffect } from 'react'

export default function ClientInput() {
  const { inputClientValue, setInputClientValue } = useInputStore()

  const handleClientChange = (e: any) => {
    setInputClientValue(e.target.value)
  }

  useEffect(() => {}, [inputClientValue])
  return (
    <input
      id='client-input'
      name='client-input'
      className='mx-2 h-[80%] w-[50%] px-2'
      value={inputClientValue}
      onChange={handleClientChange}
      placeholder='내용을 입력하세요'
    ></input>
  )
}
