'use client'

import { useInputStore } from '@/components/Zustand/accountPageStore'

export default function AccountIdInput() {
  const { inputAccountId, setInputAccountId } = useInputStore()
  const handleNoteChange = (e: any) => {
    setInputAccountId(e.target.value)
  }
  return (
    <input
      id='acountid-input'
      name='accountid-input'
      className='mx-2 h-[80%] w-[50%] px-2'
      value={inputAccountId}
      onChange={handleNoteChange}
      placeholder='AccessKey, SecretKey 입력 후 조회'
      disabled
    ></input>
  )
}
