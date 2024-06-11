'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'

export default function AccessKeyInput() {
  const { inputAccessKey, setInputAccessKey } = useInputStore()
  const handleAccountKeyInput = (e: any) => {
    setInputAccessKey(e.target.value)
  }
  return (
    <input
      id='accesskey-input'
      name='accesskey-Input'
      className='mx-2 h-[80%] w-[50%] px-2'
      value={inputAccessKey}
      onChange={handleAccountKeyInput}
      placeholder='내용을 입력하세요'
    ></input>
  )
}
